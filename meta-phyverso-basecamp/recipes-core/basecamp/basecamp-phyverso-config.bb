DESCRIPTION = "phyVERSO specific Basecamp configurations"
LICENSE = "CLOSED"

RDEPENDS:${PN}="basecamp"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
           file://config-phyverso-ac-hlc.yaml \
           file://config-phyverso-ac-pwm.yaml \
           file://config-phyverso-ac-dc.yaml \
           file://config-phyverso-ac-kit.yaml \
           file://config-phyverso-template.yaml \
           file://config-phyverso-evcs-cube.yaml \
           file://isabellenhuette-network.conf \
           file://provision-ac-kit.sh \
           file://provision-dc-kit.sh \
           "

FILES:${PN} += " \
                ${sysconfdir}/everest/* \
               "

do_install() {
    install -d ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-hlc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-pwm.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-dc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-kit.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-template.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-evcs-cube.yaml ${D}${sysconfdir}/everest/
    mkdir -p ${D}${sysconfdir}/everest/provisioning/
    install -m 0644 ${WORKDIR}/isabellenhuette-network.conf ${D}${sysconfdir}/everest/provisioning/
    install -m 0644 ${WORKDIR}/provision-ac-kit.sh ${D}${sysconfdir}/everest/provisioning/
    install -m 0644 ${WORKDIR}/provision-dc-kit.sh ${D}${sysconfdir}/everest/provisioning/
}