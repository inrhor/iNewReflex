package cn.mcres.iNewReflex.fileYaml;

import cn.mcres.iNewReflex.INewReflex;

import java.io.File;

public class CreateFile {
    private File file;
    private String name;

    public CreateFile(String name) {
        this.name = name;
    }

    public void checkAndCreate() {
        this.file = new File(INewReflex.getMain().getDataFolder(), this.name);
        if (!this.file.exists() || !this.file.isDirectory()) {
            this.file.mkdirs();
        }
    }

    public File getFile() {
        return this.file;
    }
}
