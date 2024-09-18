# How to use PhyVerso and build an Yocto image with basecamp

## Step 1: Clone this repository
Create a folder (e.g. pionix) where everything will be placed.

```
mkdir pionix
cd pionix
git clone git@github.com:PionixPro/phyverso-yocto.git
```

## Step 2: Run the setup tool
We are using a tool part of this repository (you find it in the root folder) to sync and initialize the meta layers and prepare everything for the build.
The tool supports 2 operations `init` and `sync`.

```
$ ./setup --help
usage: setup [-h] [--init] [--sync] [--sync_method {fetch,pull}]

Creates the layer structure and synchronizes it

optional arguments:
  -h, --help            show this help message and exit
  --init                Initializes the entire Yocto structure
  --sync                Synchronizes the layers to the given revision or to the given branch HEAD
  --sync_method {fetch,pull}
                        Synchronization method
```

The very first time you want to initialize the layers so all you have to do is to run the tool with the argument `--init`:
```
$ ./setup --init
```

The tool will run and sync the repositories. 
Eventually, if you make any changes to the layers or somebody made changes and you want to sync those changes locally you run it with the option `--sync`. This allows you to sync locally the changes made in the upstream. If you made changes as well to the layers you want to sync, you might want to specify how to sync (`fetch` or `pull`) so that you can have the possibility to rebase or merge the changes. By default the method is `fetch` if no argument is provided.

## Step 3: Build the image
The PhyVerso repo comes with a build directory (containing only the config folder).
In the config folder there is a default configuration file `local.conf` and the layers configuration `bblayers.conf`.

### Configure the build
There are a lot of configuration parameters available, however, the most interesting ones are:

```
# Build with Pionix EVERYTHING to synchronized to HEAD revision
BASECAMP_UNSTABLE = "1"
# Enable JavaScript and Python 
BASECAMP_JS_PY_ENABLE = "1"
```
You can change them to your needs before building.

### Build the image
To start building the image you need to source the yocto environment:

```
$ source ../source/poky/oe-init-build-env
### Shell environment set up for builds. ###

You can now run 'bitbake <target>'

Common targets are:
    core-image-minimal
    core-image-full-cmdline
    core-image-sato
    core-image-weston
    meta-toolchain
    meta-ide-support

You can also run generated qemu images with a command like 'runqemu qemux86'

Other commonly useful commands are:
 - 'devtool' and 'recipetool' handle common recipe tasks
 - 'bitbake-layers' handles common layer tasks
 - 'oe-pkgdata-util' handles common target package tasks
```
You are ready to build an image or a bundle that you can flash with rauc tool.
If you just want the image run:

```
$ bitbake phyverso-basecamp-image
```

If you want the rauc bundle, run:

```
$ bitbake phyverso-basecamp-bundle
```

Depending on your computer you might need to go for a coffee a walk or both.

## Step 4: Flash the image or the bundle on your target
If the pre-installed image on the SOM provides RAUC support you can install the bundle from within linux by executing:

```console
rauc install <URL or path to bundle file>
```


In case the currently installed linux image on the SOM does not support RAUC updates, you can also flash a new wic image via USB.
Use one partition only on the USB drive and format it with ext4.

Uncompress the image if neccessary, e.g.:

```console
unxz phyverso-basecamp-image-am62-phyverso-evcs-1.wic.xz
```

Then copy the image file (.wic) onto the USB drive and insert it into the one USB port on the phyverso.
Connect to the serial console and power up the board. Press any key to stop auto boot, then you should see the u-boot shell.

Now flash in the u-boot shell:

```console
usb start
run flash_emmc
setenv wic_image phyverso-basecamp-image-am62-phyverso-evcs-1.wic
```

If you do not have the flash_emmc script, add it to the environment by copying this into u-boot shell:

```console
setenv flash_emmc 'test -n ${BOOT_ORDER} || setenv BOOT_ORDER system0 system1;test -n ${BOOT_system0_LEFT} || setenv BOOT_system0_LEFT 3;ext4size usb 0:1 ${wic_image};setenv counter 0;setenv offset 0;setenv block_number 0x2000;setexpr bytes_left ${filesize} - ${offset};echo start: ${bytes_left};while itest ${bytes_left}  > 0;do echo rest: ${bytes_left};setexpr copy_bytes ${block_number} * 0x200;ext4load usb 0:1 0xa0000000 ${wic_image} ${copy_bytes} ${offset};mmc write 0xa0000000 ${counter} ${block_number};             setexpr counter ${counter} + ${block_number};setexpr offset ${offset} + ${copy_bytes};echo ${counter};echo ${offset};if itest ${bytes_left} < ${copy_bytes}; then setenv bytes_left 0;else setexpr bytes_left ${bytes_left} - ${copy_bytes};fi;done;'
```

Writing is really fast. Once done, reset the board, it should now boot into the newly installed image.

If it does not find the root partition after boot, set up rauc booting:

```console
env  default –a
setenv doraucboot 1
saveenv
```

The bootloader in the EMMC boot partition is not updated with the flash_emmc script.
To write the latest boot loader into the emmc boot partition from Linux, use:

```console
echo 0 > /sys/class/block/mmcblk0boot0/force_ro
mount /dev/mmcblk0p1 /boot/

dd if=/boot/tiboot3.bin of=/dev/mmcblk0boot0 count=1024 conv=fsync
dd if=/boot/tispl.bin of=/dev/mmcblk0boot0 seek=1024 count=3072 conv=fsync
dd if=/boot/u-boot.img of=/dev/mmcblk0boot0 seek=5120 count=3072 conv=fsync
```

If you are using a blank new device you will need to enable emmc boot partition boot first:
This is only once per lifetime.

```console

mmc bootpart enable 1 1 /dev/mmcblk0
mmc bootbus set single_backward x1 x8 /dev/mmcblk0
mmc hwreset enable /dev/mmcblk0
```

Updating of bootloader and emmc boot partition configuration is also possible from within u-boot shell. This process is described in [here](https://docs.phytec.com/projects/yocto-phycore-am62x/en/bsp-yocto-ampliphy-am62x-pd23.2.1/installos/flashEMMC.html#flash-emmc-from-usb-flash-drive-in-u-boot).

When flashing via USB the system will fail to boot because the flashing process can not fully write the overlay partition on ```/dev/mmcblk0p8```. On first boot into your new linux image you will be put into an emergency shell where you can format said partition and the system will boot normally afterwards (and after ```exit```ing from the emergency shell). So execute the following from within the emergency shell:

```console
mkfs.ext4 /dev/mmcblk0p8
exit
```

## Step 5: Flashing the MSPM0

Before trying to flash the MSPM0 via UART from within the linux system, make sure that no other process (e.g. basecamp or phyverso_cli) is currently using the serial `/dev/ttyS6`. 

```console
systemctl stop basecamp && killall phyverso_cli
```

If you have not already copied the phyverso-firmware.bin you can do so e.g. via SCP from your host system (.bin file is inside Debug/ or Release/ respectively):

```console
scp Debug/phyverso-firmware.bin root@192.168.3.11:/root/
```

Assuming you transfered the binary to /root/ you can then flash the firmware using the `MSPM0_bsl_flasher` tool from within the phyverso linux system.

```console
MSPM0_bsl_flasher flash -i <path to firmware bin> -p /dev/ttyS6
```

Depending on the debug flags of the currently employed `MSPM0_bsl_flasher` version on target you should see a similiar output at the end of a successful flashing process:

```console
Status:
        Programmed: 1
        Verified: 1
        Started: 1
```

The `MSPM0_bsl_flasher` tool automatically starts the application after successful flashing. You can also check if flashing was successful by running `phyverso_cli /dev/ttyS6 /etc/everest/example_config_qwello.json` and check if you receive state updates.

In case of errors try repeating the flashing process and make sure serial is not used by other processes. Also make sure that no other device is actively or passively in control of the nRST or BSL pins. It is a known problem that a XDS110 debugging probe that is attached to the uC target but not plugged into USB is preventing a successful connection to the BSL device or any other startup.
