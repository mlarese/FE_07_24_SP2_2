package it.epicode.fe_07_24_sp2_2.cloudinary;

import lombok.Data;

@Data
public class FileDto {
    private String name;
    private String url;
    private String type;
    private long size;
}
