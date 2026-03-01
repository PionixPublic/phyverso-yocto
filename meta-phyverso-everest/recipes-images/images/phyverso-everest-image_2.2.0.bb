require recipes-images/images/phyverso-everest-base.bb
SUMMARY =  "Image for phyVERSO, no specific charging hardware configuration"

add_config_symlink() {
    ln -s -r ${IMAGE_ROOTFS}/etc/everest/config-phyverso-template.yaml ${IMAGE_ROOTFS}/etc/everest/everest.yaml
}
ROOTFS_POSTPROCESS_COMMAND:append = " add_config_symlink; "