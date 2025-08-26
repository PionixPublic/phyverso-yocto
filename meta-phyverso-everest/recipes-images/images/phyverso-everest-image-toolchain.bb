require recipes-images/images/phytec-headless-image.bb

SUMMARY =  "This image is designed to contain minimal packages to build a toolchain for phyVERSO"

IMAGE_FEATURES += "\
    splash \
    ssh-server-openssh \
"

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-base \
    everest \
    mosquitto \
    tzdata \
    nodejs \
    python3 \
"

SDKIMAGE_FEATURES:remove = "dbg-pkgs src-pkgs"
IMAGE_ROOTFS_EXTRA_SPACE:append = " + 500000"
