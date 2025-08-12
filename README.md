# phyVERSO BaseCamp Yocto Image

## How to build a Yocto image with BaseCamp for phyVERSO

### Step 0: Preparations when using git.pionix.com

If you are using git.pionix.com to access this repository, you will have to add your SSH keys at https://git.pionix.com/user/settings/keys.

> [!IMPORTANT]
> Please only use the SSH URL to access the repositories on git.pionix.com
> because using HTTPS can break things down the line.

During building, the build process will need your SSH key password for
accessing BaseCamp sources and library repositories.
To also make the non-interactive phases of the build process use your password,
you will have to set your SSH key password in the current SSH session:

```
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/your_key_file
```

Please adjust the call of `ssh-add` to use your existing SSH key file.

After that, you will be prompted for entering your password for the SSH key
file.

This will start the ssh-agent and adds your SSH key file to your local SSH
session.

> [!TIP]
> For more information, see
> https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent


### Step 1: Clone this repository
Create a folder (e.g. pionix) where everything will be placed.

```
mkdir pionix
cd pionix
git clone ssh://forgejo@git.pionix.com/Pionix/phyverso-yocto.git
```

If you are using the PionixPro/phyverso-yocto repository on GitHub, you will have to clone this URL:

```
git clone git@github.com:PionixPro/phyverso-yocto.git
```



### Step 1.1: Modifications needed when using PionixPro

When using the PionixPro/phyverso-yocto repository on GitHub, you will have to uncomment/set the following parameters in `build/conf/local.conf`:

```
GIT_REPOSITORY_URL = "git://git@github.com/PionixPro"
PREFIX_GIT_REPOSITORY = ""
```

> [!NOTE]
> For now, the PionixCloud repository is not mirrored and only used for
> internal builds and not available on the Pionix Portal to build yourself.
> Get in touch with us for more information or help on building with
> PionixCloud using the repository on GitHub.

### Step 2: Run the setup tool 
To sync and initialize the meta layers and prepare everything for the build,
we are using a tool, which is part of this repository.
You can find it in the root folder.
The tool supports the two operations `init` and `sync`.

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

The very first time you want to initialize the layers, all you have to do is to run the tool with the argument `--init`:

```
$ ./setup --init
```

The tool will run and sync the repositories.

Eventually, if you make any changes to the layers or somebody made changes and
you want to sync those changes locally, you run it with the option `--sync`.
This allows you to locally sync the changes made in the upstream.
If you made changes as well to the layers you want to sync, you might want to
specify how to sync (`fetch` or `pull`) so that you can have the possibility to
rebase or merge the changes.
By default, the method is `fetch` if no argument is provided.


### Step 3: Build the image and SDK

The phyVERSO repo comes with a build directory (containing only the config folder).
In the config folder there is a default configuration file `local.conf` and the layers configuration `bblayers.conf`.

#### Configure the build
There are a lot of configuration parameters available.
However, the most interesting ones are:

```
# Build with Pionix EVERYTHING synchronized to HEAD revision
BASECAMP_UNSTABLE = "1"
# Enable JavaScript and Python 
BASECAMP_JS_PY_ENABLE = "1"
```

You can change the parameters to your needs before building.

#### Build the image
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

You can also run generated qemu images with a command like 'runqemu qemux86'.

Other commonly useful commands are:
 - 'devtool' and 'recipetool' handle common recipe tasks
 - 'bitbake-layers' handles common layer tasks
 - 'oe-pkgdata-util' handles common target package tasks
```

You are ready to build an image or a bundle that you can flash with the RAUC tool.

If you just want the image run:

```
$ bitbake phyverso-basecamp-image
```

If you want the RAUC bundle, run:

```
$ bitbake phyverso-basecamp-bundle
```

Depending on your computer, you might need to go for a coffee a walk or both.

#### Building the SDK
After sourcing the yocto environment you can also build an SDK by executing

```
$ bitbake phyverso-basecamp-image -c populate_sdk
```

### Step 4: Flash the image or the bundle on your target
If the pre-installed image on the SOM provides RAUC support, you can install the bundle from within Linux by executing:

```console
rauc install <URL or path to bundle file>
```

In case the currently installed Linux image on the SOM does not support RAUC updates, you can also flash a new wic image via USB.
Use one partition only on the USB drive and format it with ext4.

Uncompress the image if neccessary, e.g.:

```console
unxz phyverso-basecamp-image-am62-phyverso-evcs-1.wic.xz
```

Then copy the image file (.wic) onto the USB drive and insert it into the one USB port on the phyVERSO.
Connect to the serial console and power up the board. Press any key to stop auto boot, then you should see the u-boot shell.

Now flash in the u-boot shell:

```console
usb start
setenv wic_image phyverso-basecamp-image-am62-phyverso-evcs-1.wic
run flash_emmc
```

If you do not have the flash_emmc script, add it to the environment by copying this into u-boot shell:

```console
setenv flash_emmc 'test -n ${BOOT_ORDER} || setenv BOOT_ORDER system0 system1;test -n ${BOOT_system0_LEFT} || setenv BOOT_system0_LEFT 3;ext4size usb 0:1 ${wic_image};setenv counter 0;setenv offset 0;setenv block_number 0x2000;setexpr bytes_left ${filesize} - ${offset};echo start: ${bytes_left};while itest ${bytes_left}  > 0;do echo rest: ${bytes_left};setexpr copy_bytes ${block_number} * 0x200;ext4load usb 0:1 0xa0000000 ${wic_image} ${copy_bytes} ${offset};mmc write 0xa0000000 ${counter} ${block_number};             setexpr counter ${counter} + ${block_number};setexpr offset ${offset} + ${copy_bytes};echo ${counter};echo ${offset};if itest ${bytes_left} < ${copy_bytes}; then setenv bytes_left 0;else setexpr bytes_left ${bytes_left} - ${copy_bytes};fi;done;'
```

Writing is really fast. Once done, reset the board. It should now boot into the newly installed image.

If it does not find the root partition after boot, set up RAUC booting:

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

If you are using a blank new device, you will need to enable emmc boot partition boot first:
This is only once per lifetime.

```console

mmc bootpart enable 1 1 /dev/mmcblk0
mmc bootbus set single_backward x1 x8 /dev/mmcblk0
mmc hwreset enable /dev/mmcblk0
```

Updating of bootloader and emmc boot partition configuration is also possible
from within u-boot shell. 
This process is described [here](https://docs.phytec.com/projects/yocto-phycore-am62x/en/bsp-yocto-ampliphy-am62x-pd23.2.1/installos/flashEMMC.html#flash-emmc-from-usb-flash-drive-in-u-boot).

When flashing via USB, the system will fail to boot because the flashing
process cannot fully write the overlay partition on ```/dev/mmcblk0p8```.
On first boot into your new Linux image, you will be put into an emergency
shell where you can format said partition and the system will boot normally
afterwards (and after ```exit```ing from the emergency shell).

So execute the following from within the emergency shell:

```console
mkfs.ext4 /dev/mmcblk0p8
exit
```

### Step 5: Flashing the MSPM0 (optional)

If you are using an image release that is newer than alpha-3 (for example 1.0.0
and up), the image will already have the firmware file located at
`/usr/lib/firmware/phyverso-firmware.bin`.
This file will automatically get flashed, if needed, while Linux is booting via
the `phyverso-mcu-bringup.service`.
You can check if it has succeeded using `systemctl status phyverso-mcu-bringup.service`.

You can still always try and flash a different firmware file manually, but if
you don't replace the file `/usr/lib/firmware/phyverso-firmware.bin`, it will
get overwritten the next time you reboot.

Before trying to flash the MSPM0 via UART from within the Linux system, make
sure that no other process (e.g. basecamp or phyverso_cli) is currently using
the serial `/dev/ttyS6`. 

```console
systemctl stop basecamp && killall phyverso_cli
```

If you have not already copied the phyverso-firmware.bin you can do so e.g. via SCP from your host system (.bin file is inside Debug/ or Release/ respectively):

```console
scp Debug/phyverso-firmware.bin root@192.168.3.11:/root/
```

Assuming you transfered the binary to /root/, you can then flash the firmware
using the `MSPM0_bsl_flasher` tool from within the phyVERSO Linux system.

```console
MSPM0_bsl_flasher flash -i <path to firmware bin> -p /dev/ttyS6
```

Depending on the debug flags of the currently employed `MSPM0_bsl_flasher`
version on target, you should see a similiar output at the end of a successful
flashing process:

```console
Status:
        Programmed: 1
        Verified: 1
        Started: 1
```

The `MSPM0_bsl_flasher` tool automatically starts the application after
successful flashing.
You can also check if flashing was successful by running
`phyverso_cli /dev/ttyS6 /etc/everest/example_config_qwello.json` and check if
you receive state updates.

In case of errors, try repeating the flashing process and make sure serial is
not used by other processes.
Also make sure that no other device is actively or passively in control of the
nRST or BSL pins.
It is a known problem that a XDS110 debugging probe that is attached to the uC
target but not plugged into USB is preventing a successful connection to the
BSL device or any other startup.

## Building WIC images on kirkstone-based releases

At the moment it is not possible to build wic images without manual changes
because in PHYTECs most recent AM62x kirkstone BSP layers, they remove
`wic.xz wic.bmap` in a way that those `IMAGE_FSTYPES` cannot be added anywhere
else in another layer, local.conf or recipe.
You will have to manually go to the
`sources/meta-phytec/conf/machine/include/phyk3.inc b/conf/machine/include/phyk3.inc`
file and uncomment the lines shown in the following diff:

```console
diff --git a/conf/machine/include/phyk3.inc b/conf/machine/include/phyk3.inc
index 66420faf..c76b53c4 100644
--- a/conf/machine/include/phyk3.inc
+++ b/conf/machine/include/phyk3.inc
@@ -56,6 +56,6 @@ IMAGE_CLASSES += "image-types-partup"
 include partup-layout-config.inc
 IMAGE_FSTYPES += "partup"

-IMAGE_FSTYPES:remove:update = "wic.xz wic.bmap"
+#IMAGE_FSTYPES:remove:update = "wic.xz wic.bmap"
 # rootfs alignment required for RAUC adaptive update
 IMAGE_ROOTFS_ALIGNMENT:update = "4"
```

This problem is known to PHYTEC and has been fixed with scarthgap releases,
which we will migrate soon with our upcoming 2.x.x releases and following.

## How to integrate different overlays (display/wifi)

Some added hardware functionality and devices might require you to set a
u-boot environment variable for u-boot and Linux to find and add the respective
device tree overlays. 
You can see which overlays are activated by executing `fw_printenv overlays`
inside the shell.
The overlays variable can be set with

`fw_setenv overlays "<list of whitespace separated dtbo files>"`

So for example, to activate the cc33xx WiFi module and an lvds attached
display, the command to execute would look like this:

```console
fw_setenv overlays "cc33xx.dtbo dd0700mc01_lvds.dtbo"
```

In the current 1.0.1 release we do not yet support u-boot uEnv.txt files to
specify the selected overlays to be used.
We plan on adding this feature with the next major release that will also
probably be in conjunction with moving up to Scarthgap.

When trying to add custom overlays, mind that in the current 1.0.1 release,
the boot partitions that u-boot sees are not accessible from `/boot` when
inside a shell but are actually either `/dev/mmcblk0p1` or `/dev/mmcblk0p2`,
which will have to be mounted manually to move overlays there for u-boot to be
found, or get in touch with Pionix to have us look at integrating your
overlays directly into an image.

Which `/dev/mmcblk0pX` partition you need to mount will depend on which RAUC
slot you are on.
You can see the active one by executing `rauc status` and mount the boot.X
partition accordingly.
