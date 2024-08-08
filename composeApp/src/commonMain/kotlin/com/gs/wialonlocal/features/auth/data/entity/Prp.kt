package com.gs.wialonlocal.features.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Prp(
    val access_templates: String,
    val autocomplete: String,
    val dst: String,
    val forceAddedDashboardTabOnce: String,
    val fpnl: String,
    val geodata_source: String,
    val hbacit: String,
    val hpnl: String,
    val language: String,
    val m_ml: String,
    val m_mm: String,
    val m_mm2: String,
    val m_monu: String,
    val m_ms: String,
    val minimap_zoom_level: String,
    val mongr: String,
    val mont: String,
    val monu: String,
    val monuv: String,
    val mtg: String,
    val mtve: String,
    val mu_battery: String,
    val mu_dev_cfg: String,
    val mu_fast_report: String,
    val mu_fast_report_tmpl: String,
    val mu_fast_track_ival: String,
    val mu_sens: String,
    val mu_tbl_cols_sizes: String,
    val mu_tbl_sort: String,
    val mu_tracks: String,
    val radd: String,
    val show_log: String,
    val tz: String,
    val umap: String,
    val us_addr_fmt: String,
    val used_hw: String,
    val znsvlist: String
)