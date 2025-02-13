require recipes-images/images/phytec-headless-image.bb

SUMMARY =  "This image is designed to contain a complete Basecamp install for phyVERSO"

IMAGE_FEATURES += "\
    splash \
    ssh-server-openssh \
"

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-machine-base \
    basecamp \
    basecamp-config \
    basecamp-service \
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
    basecamp-phyverso-config \
"

SDKIMAGE_FEATURES:remove = "dbg-pkgs src-pkgs"
#IMAGE_ROOTFS_EXTRA_SPACE:append = " + 500000"

IMAGE_INSTALL:append_am62 = " firmwared"

WKS_FILE:forcevariable = "phyverso-basecamp-rauc-sdimage.wks"