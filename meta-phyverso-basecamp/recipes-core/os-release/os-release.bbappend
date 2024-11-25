def get_layer_rev(d):
    return bb.process.run('git rev-parse HEAD')

OS_RELEASE_FIELDS += "BUILD_VERSION"
OS_RELEASE_FIELDS += "LAYER_REV"
BUILD_VERSION = "1.0.0"
LAYER_REV="${@get_layer_rev(d)[0].rstrip()}"
VERSION = "BaseCamp phyVERSO-EVCS ${BUILD_VERSION} ${LAYER_REV}${@' (%s)' % DISTRO_CODENAME if 'DISTRO_CODENAME' in d else ''}"

# Ensure the git commands run every time bitbake is invoked.
BB_DONT_CACHE = "1"