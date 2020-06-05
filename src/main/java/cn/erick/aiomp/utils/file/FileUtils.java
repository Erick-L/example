package cn.erick.aiomp.utils.file;

import cn.erick.aiomp.common.exception.CustomException;
import cn.erick.aiomp.common.exception.UnifiedExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件处理工具类
 *
 * @Author Erick
 * @Date 2020/5/22 17:00
 * @Version 1.0
 */
public class FileUtils {

    //日志
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    //返回值
    private static Map<String, Object> backMap = null;
    //设置目标路径
    private static Path path;


    /**
     * 上传单附件
     * 文件类型 MultipartFile
     *
     * @param file       上传文件
     * @param targetPath 目标存放路径
     * @return 返回Map   code 0000为成功；0001为失败
     */
    public static ResponseEntity<Map> uploadFile(MultipartFile file, String targetPath) {
        String code = "";
        backMap = new HashMap<>();
        try {
            createTargetDir(targetPath);
            path = Paths.get(targetPath);
        } catch (Exception e) {
            new UnifiedExceptionHandler().exceptionHandler(e);
        }

        //接收返回判定，成功创建文件夹后进行文件保存
        try {

            String filename = file.getOriginalFilename();

            System.out.println("---------" + filename);

            boolean flag = checkFileisExist(targetPath, filename);

            if (flag) {
                //先检查该文件是否存在在对应文件夹中。如果存在。命名为（累加）
                filename = getRenameForWin(targetPath, filename);
            }

            Files.copy(file.getInputStream(), path.resolve(filename));

            backMap.put("code", "0000");

            backMap.put("path", path.resolve(filename).toFile().getPath());

        } catch (IOException e) {
            backMap.put("code", "0001");
            backMap.put("path", null);
            logger.error("保存文件失败======>" + e.getMessage());
            return ResponseEntity.badRequest().body(backMap);
        }


        return ResponseEntity.ok(backMap);


    }


    /**
     * 多文件上传
     *
     * @param files
     * @param targetPath
     * @return
     */
    public static ResponseEntity<Map> uploadFile(MultipartFile[] files, String targetPath) {

        String code = "";
        backMap = new HashMap<>();
        List<String> paths = new ArrayList<String>();

        try {
            createTargetDir(targetPath);
            path = Paths.get(targetPath);
        } catch (Exception e) {
            //交由统一异常处理
            new UnifiedExceptionHandler().exceptionHandler(e);
        }

        //接收返回判定，成功创建文件夹后进行文件保存

        try {
            for (MultipartFile file : files) {

                String filename = file.getOriginalFilename();

                System.out.println("---------" + filename);

                boolean flag = checkFileisExist(targetPath, filename);

                if (flag) {
                    //先检查该文件是否存在在对应文件夹中。如果存在。命名为（累加）
                    filename = getRenameForWin(targetPath, filename);
                }

                Files.copy(file.getInputStream(), path.resolve(filename));
                paths.add(path.resolve(filename).toFile().getPath());

            }
            backMap.put("code", "0000");
            backMap.put("path", paths);
        } catch (IOException e) {
            backMap.put("code", "0001");
            backMap.put("path", null);
            logger.error("保存文件失败======>" + e.getMessage());
            return ResponseEntity.badRequest().body(backMap);
        }


        return ResponseEntity.ok(backMap);
    }

    /**
     * 获取上传文件列表
     *
     * @param T
     * @return
     */
    public static ResponseEntity<List<UploadEntity>> getFiles(Class T) {

        List<UploadEntity> files = load()
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder.fromMethodName(T, "getFile", path.getFileName().toString()
                    ).build().toString();

                    return new UploadEntity(fileName, url);
                }).collect(Collectors.toList());

        return ResponseEntity.ok(files);
    }


    /**
     * 下载文件
     *
     * @param filename
     * @return
     */
    public static ResponseEntity<Resource> dowloadFile(String filename) {
        Resource file = load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }


    /**
     * 创建存储地址
     * code
     * 0：创建成功
     * 1：创建失败，请预先设置目标存储路径
     * 2：创建失败：目标路径不是文件夹
     * 3：创建失败，目标路径磁盘不存在
     *
     * @return
     */
    public static void createTargetDir(String targetPath) {


        if (StringUtils.isEmpty(targetPath)) {
            logger.error("创建失败，请预先设置目标存储路径");
            throw new CustomException("创建失败，请预先设置目标存储路径");


        } else {
            File file = new File(targetPath);
            if (file.isFile()) {
                logger.error("创建失败：目标路径不是文件夹");
                throw new CustomException("创建失败：目标路径不是文件夹");
            } else {

                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    path = Paths.get(targetPath);
                    logger.info("创建成功");
                } catch (Exception e) {
                    logger.error("创建失败，目标路径磁盘不存在");
                    throw new CustomException("创建失败，目标路径磁盘不存在");
                }

            }

        }

    }

    /**
     * 辅助下载文件
     *
     * @param fileName
     * @return
     */
    public static Resource load(String fileName) {
        Path file = path.resolve(fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new RuntimeException("Could not read the file");

        } catch (MalformedURLException e) {
            logger.error("载入文件失败======>" + e.getMessage());
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    /**
     * 回显文件
     *
     * @return
     */
    public static Stream<Path> load() {

        try {
            logger.info("成功载入");
            return Files.walk(path, 1)
                    .filter(path -> !path.equals(path))
                    .map(path::relativize);
        } catch (IOException e) {
            logger.error("载入流失败======>" + e.getMessage());
            throw new RuntimeException("Could not load the files.");
        }

    }


    /**
     * 获取后缀名
     *
     * @param filepath
     * @return
     */
    public static String getFilrSuffix(String filepath) {
        String suffix = filepath.substring(filepath.lastIndexOf(".") + 1);
        return suffix;
    }

    /**
     * 获取文件名，带后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileNameWithSuffix(String fileName) {
        //String fileWithSubffix = fileName.substring(fileName.lastIndexOf("\\") + 1);
       String fileWithSubffix = Paths.get(fileName).getFileName().toString();
        return fileWithSubffix;
    }

    /**
     * 获取文件名，不带后缀
     *
     * @param orgNama
     * @return
     */
    public static String getFileNameNoSuffix(String orgNama) {
        String filename = orgNama.substring(0, orgNama.indexOf("."));
        return filename;
    }

    /**
     * 检查文件是否已经存在
     *
     * @param fileDir
     * @param filename
     * @return
     */
    public static boolean checkFileisExist(String fileDir, String filename) {

        boolean flag = false;

        File file = new File(fileDir + "/" + filename);

        if (file.exists())
            flag = true;

        return flag;
    }

    /**
     * 文件重命名 格式 xxx.doc   xxx(1).doc   xxx(2).doc
     *
     * @param fileDir
     * @param fileName
     * @return
     */
    public static String getRenameForWin(String fileDir, String fileName) {

        //获取文件夹下所有文件
        File file = new File(fileDir);

        List<File> fileNames = Arrays.asList(file.listFiles());
        // 文件新命名
        String newName = "";
        // 文件后缀
        String[] split = fileName.split("\\.");
        // 定义模糊匹配的文件名
        Pattern p = Pattern.compile(split[0]);
        Matcher m;
        // 定义一个计数器用来记录文件名重复数
        int cont = 0;
        // 遍历文件名集合.用来判断文件上传
        if (fileNames.size() > 0) {
            for (int i = 0; i < fileNames.size(); i++) {
                m = p.matcher(fileNames.get(i).getName());
                boolean b = m.lookingAt();

                if (b) {
                    cont++;
                }
            }
        }
        // 新名称
        if (cont == 0) {
            newName = fileName;
        } else {
            cont += 1;
            newName = split[0] + "(" + (cont - 1) + ")" + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return newName;

    }
}

class UploadEntity {
    private String fileName;
    private String url;

    public UploadEntity(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
