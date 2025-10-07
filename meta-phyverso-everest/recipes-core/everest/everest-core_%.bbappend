SYSTEMD_SERVICE:${PN}:remove = "everest.service"

FILES:${PN} += "${systemd_system_unitdir}/"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        rm ${D}${systemd_system_unitdir}/everest.service
    fi
}

# only used temporarily until PhyVersoBSP for FW 2.0 is merged into main and meta-everest branch updated
SRC_URI = "git://github.com/EVerest/everest-core.git;branch=tst/winline;protocol=https  \
           file://everest.service \
           "
SRCREV = "4f041385de9d6ed5dde930af06c534d3050dd921"

DEPENDS = " \
    everest-cmake \
    boost \
    sigslot \
    pugixml \
    libpcap \
    evcli-native \
    rsync-native \
    nodejs-native \
    everest-framework \
    libocpp \
    libfsm \
    liblog \
    libtimer \
    libslac \
    libevent \
    libevse-security \
    libcbv2g \
    libiso15118 \
    libnfc-nci \
    curl \
    everest-sqlite \
    sdbus-c++ \
"

