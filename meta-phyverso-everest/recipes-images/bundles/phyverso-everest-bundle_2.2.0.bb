require recipes-images/bundles/phytec-base-bundle.inc
RAUC_SLOT_rootfs ?= "phyverso-everest-image"

CERT_PATH = "${OEROOT}/../phytec-dev-ca"

#RAUC_SLOT_rootfs[rename] ?= "ext4"

IMAGE_MACHINE_SUFFIX = ""
IMAGE_NAME_SUFFIX = ""
BUNDLE_NAME = "${BUNDLE_BASENAME}-${PV}-${DATETIME}"
BUNDLE_LINK_NAME = "${BUNDLE_BASENAME}"