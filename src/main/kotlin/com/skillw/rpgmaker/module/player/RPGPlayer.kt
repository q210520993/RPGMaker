package com.skillw.rpgmaker.module.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.luckperms.api.LuckPerms
import net.luckperms.api.cacheddata.CachedMetaData
import net.luckperms.api.model.data.DataMutateResult
import net.luckperms.api.model.user.User
import net.luckperms.api.node.Node
import net.luckperms.api.util.Tristate
import net.minestom.server.entity.Player
import net.minestom.server.network.player.PlayerConnection
import net.minestom.server.permission.Permission
import net.minestom.server.permission.PermissionVerifier
import java.util.*
import java.util.concurrent.CompletableFuture

/*
* LP部分来源于https://github.com/LooFifteen/LuckPerms/blob/feat/minestom/minestom/src/test/java/me/lucko/luckperms/minestom/ExamplePlayer.java
* MIT开源
* */

class RPGPlayer(val luckPerms: LuckPerms, uuid: UUID, username: String, playerConnection: PlayerConnection) : Player(uuid, username, playerConnection) {
    private val MINI_MESSAGE: MiniMessage = MiniMessage.miniMessage()
    private val playerAdapter = luckPerms.getPlayerAdapter(Player::class.java)

    override fun addPermission(permission: Permission) {
        // this method implements itself as fire-and-forget as LuckPerms
        // provides futures as responses to permission changes, which
        // Minestom does not support in this context
        this.addPermission(permission.permissionName)
    }
    fun addPermission(permission: String): CompletableFuture<DataMutateResult> {
        val user = this.getLuckPermsUser()
        val result = user.data().add(Node.builder(permission).build())
        return luckPerms.userManager.saveUser(user).thenApply { result }
    }

    private fun getLuckPermsMetaData(): CachedMetaData {
        return getLuckPermsUser().cachedData.metaData
    }

    /**
     * Removes a permission from the player.
     *
     * @param permission the permission to remove
     */
    @Deprecated("the {@link Permission} object is not used in the LuckPerms implementation", ReplaceWith("this.removePermission(permission.permissionName)"))
    override fun removePermission(permission: Permission) {
        // this method implements itself as fire-and-forget as LuckPerms
        // provides futures as responses to permission changes, which
        // Minestom does not support in this context
        this.removePermission(permission.permissionName)
    }

    /**
     * Removes a permission from the player. You may choose not to implement
     * this method on a production server, and leave permission management
     * to the LuckPerms web interface or in-game commands.
     *
     * @param permissionName the name of the permission to remove
     */
    @Deprecated("the overridden method does not return a future")
    override fun removePermission(permissionName: String) {
        val user = this.getLuckPermsUser()
        user.data().remove(Node.builder(permissionName).build())
        luckPerms.userManager.saveUser(user)
    }

    /**
     * Removes a permission from the player. You may choose not to implement
     * this method on a production server, and leave permission management
     * to the LuckPerms web interface or in-game commands.
     *
     * @param permissionName the name of the permission to remove
     * @param ignored ignored parameter to differentiate from the overridden method
     */
    fun removePermission(permissionName: String, ignored: Void?): CompletableFuture<DataMutateResult> {
        val user = this.getLuckPermsUser()
        val result = user.data().remove(Node.builder(permissionName).build())
        return luckPerms.userManager.saveUser(user).thenApply { ignored0: Void? -> result }
    }

    /**
     * Checks if the player has a permission.
     *
     * @param permission the permission to check
     * @return true if the player has the permission
     */
    @Deprecated("the {@link Permission} object is not used in the LuckPerms implementation", ReplaceWith("this.hasPermission(permission.permissionName)")
    )
    override fun hasPermission(permission: Permission): Boolean {
        return this.hasPermission(permission.permissionName)
    }

    /**
     * Gets a permission from the player.
     *
     * @param permissionName the name of the permission to check
     * @return the permission if the player has it, or null if not
     */
    @Deprecated("the {@link Permission} object is not used in the LuckPerms implementation")
    override fun getPermission(permissionName: String): Permission? {
        if (!this.hasPermission(permissionName)) return null
        return Permission(permissionName)
    }

    /**
     * Checks if the player has a permission.
     *
     * @param permissionName the name of the permission to check
     * @param permissionVerifier the permission verifier, unused
     * @return true if the player has the permission
     */
    @Deprecated(
        """the {@link PermissionVerifier} interface checks for NBT data, which is not
      used in the LuckPerms implementation""", ReplaceWith("this.hasPermission(permissionName)")
    )
    override fun hasPermission(permissionName: String, permissionVerifier: PermissionVerifier?): Boolean {
        return this.hasPermission(permissionName)
    }

    /**
     * Checks if the player has a permission.
     *
     * @param permissionName the name of the permission to check
     * @return true if the player has the permission
     */
    override fun hasPermission(permissionName: String): Boolean {
        return this.getPermissionValue(permissionName).asBoolean()
    }

    /**
     * Gets the value of a permission. This passes a [Tristate] value
     * straight from LuckPerms, which may be a better option than using
     * boolean values in some cases.
     *
     * @param permissionName the name of the permission to check
     * @return the value of the permission
     */
    private fun getPermissionValue(permissionName: String): Tristate {
        val user = this.getLuckPermsUser()
        return user.cachedData.permissionData.checkPermission(permissionName)
    }

    /**
     * Gets the prefix of the player. This method uses the MiniMessage library
     * to parse the prefix, which is a more advanced option than using legacy
     * chat formatting.
     *
     * @return the prefix of the player
     */
    fun getPrefix(): Component {
        val prefix = getLuckPermsMetaData().prefix ?: return Component.empty()
        return MINI_MESSAGE.deserialize(prefix)
    }

    /**
     * Gets the suffix of the player. This method uses the MiniMessage library
     * to parse the suffix, which is a more advanced option than using legacy
     * chat formatting.
     *
     * @return the suffix of the player
     */
    fun getSuffix(): Component {
        val suffix = getLuckPermsMetaData().suffix ?: return Component.empty()
        return MINI_MESSAGE.deserialize(suffix)
    }

    private fun getLuckPermsUser(): User {
        return this.playerAdapter.getUser(this)
    }

}