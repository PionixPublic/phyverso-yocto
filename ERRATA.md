# ERRATA

## DM R5F 49d bug

With PHYTECs PD24.x.x and below BSP versions exists a bug in the low-level R5F DM firmware, which leads to unrecoverable crashes after roughly 49 days. More information can be found on the TI forums: https://e2e.ti.com/support/processors-group/processors/f/processors-forum/1511929/faq-dm-r5f-can-crash-in-certain-conditions-am62x-am62ax-am62dx-am62px-am67-am67a

Using PHYTECs PD25.x.x BSP releases will have a fix for that but currently introduces faulty behaviour with peripherals, that has not been adressed by PHYTEC in their `meta-phyverso-evcs` layer yet at the time of the [2.2.0] release -> see [issue #7](https://github.com/phytec/meta-phyverso-evcs/issues/7#issue-3990336119).

Once this is fixed on the PHYTEC layer side, feel free to integrate the newest PD25.x.x release by changing the `setup` script accordingly and open a PR on this repository after thorough testing.