FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:prepend = "\ 
    file://cg5317-host.service \
    file://check_modems_up.sh \
"

do_install:append() {
    install -m 0755 ${WORKDIR}/check_modems_up.sh ${D}${sysconfdir}/systemd/system
}