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
# also even more off-main now -> bender fix integrated before PR is merged
SRC_URI = " \
    git://github.com/EVerest/everest-core.git;branch=feat/bender_l2earth;protocol=https \
    file://everest.service \
"
SRCREV = "4dd5daa2cb4ff5af7f675d0d80260156448fd473"

# needed for BU modules
DEPENDS += "ftxui"