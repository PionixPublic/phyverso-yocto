require recipes-images/images/phytec-headless-image.bb

SUMMARY =  "This image is designed to contain a complete EVerest install for phyVERSO"

IMAGE_FEATURES += "\
    splash \
    ssh-server-openssh \
"

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-machine-base \
    everest-core \
    mosquitto \
    tzdata \
    lms-eth2spi \
    open-plc-utils \
    cg5317-utils \
    mspm0-bsl-flasher \
    packagegroup-update \
    packagegroup-rt \
    packagegroup-core-boot \
    packagegroup-sks-openssl-tpm2 \
    rauc \
    tcpdump \
    canutils \
    htop \
    overlayfs \
    tmux \
    phyverso-mcu-bringup-service \
    phyverso-firmware \
    everest-phyverso-config \
    flutter-pi \
    flutter-engine \
    nano \
    display-app \
    ti-cc33xx-firmware \
    packagegroup-virtualization \
"

#TODO/add here:
#   - basecamp-service fix
#   - basecamp-config fix
# everest-service not yet working
IMAGE_INSTALL += "\
    everest-service \
"

SDKIMAGE_FEATURES:remove = "dbg-pkgs src-pkgs"
#IMAGE_ROOTFS_EXTRA_SPACE:append = " + 500000"

IMAGE_INSTALL:append_am62 = " firmwared"

WKS_FILE:forcevariable = "phyverso-everest-rauc-sdimage.wks"

inherit extrausers
EXTRA_USERS_PARAMS = "\
    usermod -p '\$1\$3t3Pos3u\$rJcKiN./w.sbD41oMqQM71' root; \
    "

EXTRA_IMAGECMD:ext4:append = " -O ^orphan_file"

IMAGE_FSTYPES:append:update = " wic.xz wic.bmap"