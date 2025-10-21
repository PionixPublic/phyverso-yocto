# meta-everest adds its own everest.service file, we will remove that here because we want to split everest.service and it's installation into a separate recipe
SYSTEMD_SERVICE:${PN}:remove = "everest.service"

FILES:${PN} += "${systemd_system_unitdir}/"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        rm ${D}${systemd_system_unitdir}/everest.service
    fi
}

# only temporarily modify SRC_URI/SRCREV until meta-everest 2025.10.0 is released which will include Winline driver and PhyVersoBSP v2.0.0 FW support
SRC_URI = "git://github.com/EVerest/everest-core.git;branch=main;protocol=https"
SRCREV = "374030cb3e1709f436fec5fc3874a95533173f07"