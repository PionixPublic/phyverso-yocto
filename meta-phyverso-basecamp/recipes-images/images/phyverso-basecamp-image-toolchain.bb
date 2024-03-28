require recipes-images/images/phytec-headless-image.bb

SUMMARY =  "This image is designed to contain a complete everest install for phyverso"

IMAGE_FEATURES += "\
    splash \
    ssh-server-openssh \
"

LICENSE = "MIT"

IMAGE_INSTALL += "\
    packagegroup-base \
    basecamp \
    mosquitto \
    tzdata \
    nodejs \
    python3 \
"

SDKIMAGE_FEATURES:remove = "dbg-pkgs src-pkgs"
IMAGE_ROOTFS_EXTRA_SPACE:append = " + 500000"
