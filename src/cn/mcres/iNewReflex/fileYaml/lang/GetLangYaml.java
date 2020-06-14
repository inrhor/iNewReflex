package cn.mcres.iNewReflex.fileYaml.lang;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class GetLangYaml {
    public static List<String> COMMAND_CONSOLE, COMMAND_HELP, COMMAND_NO_PERMISSION, PLAYER_NOT_ONLINE, COMMAND_RELOAD,
            COMMAND_GIVE_ERROR_FORMAT, COMMAND_SPAWN_BLOCK, BLOCK_SPAWN_WRONG_FORMAT,
            ID_NOT_EXIST, SUCCESSFULLY_GIVE, TYPE_NOT_EXIST, NOT_INT, RANDOM_SPAWN_BLOCK_IS_OLD_VERSION;

    public static List<String> PANEL_NOT_EXIST, RECIPE_NOT_EXIST, OPEN_WRONG_FORMAT,
            VIEW_WRONG_FORMAT, ADD_WRONG_FORMAT, DEL_WRONG_FORMAT, EDIT_WRONG_FORMAT,
            RECIPE_EXISTED, RECIPE_SUCCESSFULLY_DEL, RECIPE_SUCCESSFULLY_EDIT;

    public static void get() {
        final FileConfiguration langYaml = CreateLangYaml.langYaml;

        COMMAND_CONSOLE = langYaml.getStringList("COMMAND_CONSOLE");
        COMMAND_HELP = langYaml.getStringList("COMMAND_HELP");
        COMMAND_NO_PERMISSION = langYaml.getStringList("COMMAND_NO_PERMISSION");
        PLAYER_NOT_ONLINE = langYaml.getStringList("PLAYER_NOT_ONLINE");
        COMMAND_RELOAD = langYaml.getStringList("COMMAND_RELOAD");
        COMMAND_SPAWN_BLOCK = langYaml.getStringList("COMMAND_SPAWN_BLOCK");
        RANDOM_SPAWN_BLOCK_IS_OLD_VERSION = langYaml.getStringList("RANDOM_SPAWN_BLOCK_IS_OLD_VERSION");

        COMMAND_GIVE_ERROR_FORMAT = langYaml.getStringList("COMMAND_GIVE_ERROR_FORMAT");
        BLOCK_SPAWN_WRONG_FORMAT = langYaml.getStringList("BLOCK_SPAWN_WRONG_FORMAT");

        ID_NOT_EXIST = langYaml.getStringList("ID_NOT_EXIST");
        SUCCESSFULLY_GIVE = langYaml.getStringList("SUCCESSFULLY_GIVE");
        TYPE_NOT_EXIST = langYaml.getStringList("TYPE_NOT_EXIST");
        NOT_INT = langYaml.getStringList("NOT_INT");

        PANEL_NOT_EXIST = langYaml.getStringList("PANEL_NOT_EXIST");
        RECIPE_NOT_EXIST = langYaml.getStringList("RECIPE_NOT_EXIST");
        OPEN_WRONG_FORMAT = langYaml.getStringList("OPEN_WRONG_FORMAT");
        VIEW_WRONG_FORMAT = langYaml.getStringList("VIEW_WRONG_FORMAT");
        ADD_WRONG_FORMAT = langYaml.getStringList("ADD_WRONG_FORMAT");
        DEL_WRONG_FORMAT = langYaml.getStringList("DEL_WRONG_FORMAT");
        EDIT_WRONG_FORMAT = langYaml.getStringList("EDIT_WRONG_FORMAT");
        RECIPE_EXISTED =  langYaml.getStringList("RECIPE_EXISTED");
        RECIPE_SUCCESSFULLY_DEL =  langYaml.getStringList("RECIPE_SUCCESSFULLY_DEL");
        RECIPE_SUCCESSFULLY_EDIT =  langYaml.getStringList("RECIPE_SUCCESSFULLY_EDIT");
    }
}
