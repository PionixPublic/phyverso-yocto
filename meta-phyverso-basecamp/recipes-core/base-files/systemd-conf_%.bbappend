FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

inherit systemd

SRC_URI += " \
    file://can0.network \
    file://can1.network \
"
