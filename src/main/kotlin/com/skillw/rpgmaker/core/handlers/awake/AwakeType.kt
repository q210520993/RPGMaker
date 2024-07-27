package com.skillw.rpgmaker.core.handlers.awake

enum class AwakeType {
    //服务器初始化时(Manager初始化,插件进入onInit阶段)
    Load,
    //服务器地图[放在本地资源管理器持久化存储的地图]加载完时(Plugin onEnable)
    Enable,
    //服务器完全加载完时[可以进入玩家了]
    Active,
    //服务器关闭时
    Disable
}