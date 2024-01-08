package org.acme.util;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileData {
    private String fileName;
    private String fileNameExtention; 
    private File file;
}
