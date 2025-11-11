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
    libocpp \
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
    everest-service \
    phyverso-mcu-bringup-service \
    phyverso-firmware \
    everest-phyverso-config \
    nano \
    ti-cc33xx-firmware \
    ${@bb.utils.contains("PHYVERSO_USE_VIRTUALIZATION", "1", "packagegroup-virtualization", "", d)} \
    ${@bb.utils.contains("PHYVERSO_USE_DISPLAY_APP", "1", "packagegroup-pionix-display-app", "", d)} \
    ${@bb.utils.contains("PHYVERSO_SYSFS_USER_GPIOS", "1", "sysfs-user-gpios", "", d)} \
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

IMAGE_FSTYPES:append:update = "${@bb.utils.contains("PHYVERSO_BUILD_WIC", "1", " wic.xz wic.bmap", "", d)}"