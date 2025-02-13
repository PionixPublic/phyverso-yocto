require recipes-images/images/phyverso-basecamp-base.bb
SUMMARY =  "Image for PHYTEC AC Kit"

add_config_symlink() {
    ln -s -r ${IMAGE_ROOTFS}/etc/everest/config-phyverso-ac-kit.yaml ${IMAGE_ROOTFS}/etc/everest/basecamp.yaml
}
ROOTFS_POSTPROCESS_COMMAND:append = " add_config_symlink; "