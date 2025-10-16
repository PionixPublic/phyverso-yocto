# meta-everest adds its own everest.service file, we will remove that here because we want to split everest.service and it's installation into a separate recipe
SYSTEMD_SERVICE:${PN}:remove = "everest.service"

FILES:${PN} += "${systemd_system_unitdir}/"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        rm ${D}${systemd_system_unitdir}/everest.service
    fi
}

# only temporarily set off-main branch, until winline driver is merged into everest-core/main and this everest-core version is also included in a meta-everest release
SRC_URI = "git://github.com/EVerest/everest-core.git;branch=feature/winline_power_supply;protocol=https"
# to be updated once PR is cleaned up
SRCREV = "b2e17fac4ab9f1870c6a6ac0ef235dc227970420"