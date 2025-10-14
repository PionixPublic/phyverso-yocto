# remove mcan files from meta-ampliphy layer because they are added in meta-phyverso-evcs already as
# `main_mcan.network` but `11-main_mcan.network` would match first, so they would not have any effect
SRC_URI:remove:k3 = " \
    file://11-main_mcan.network \
    file://11-mcu_mcan.network \
"