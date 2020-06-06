package org.basic.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveEntry;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveEntry;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

public class CompressUtils {

    enum CompressType {
        ar("ar", false, ArArchiveEntry.class, ArArchiveInputStream.class, ArArchiveOutputStream.class),
        arj("arj", false, ArjArchiveEntry.class, ArjArchiveInputStream.class, null),
        cpio("cpio", false, CpioArchiveEntry.class, CpioArchiveInputStream.class, CpioArchiveOutputStream.class),
        dump("dump", false, DumpArchiveEntry.class, DumpArchiveInputStream.class, null),
        jar("jar", false, JarArchiveEntry.class, JarArchiveInputStream.class, JarArchiveOutputStream.class),
        tar("tar", false, TarArchiveEntry.class, TarArchiveInputStream.class, TarArchiveOutputStream.class),
        zip("zip", false, ZipArchiveEntry.class, ZipArchiveInputStream.class, ZipArchiveOutputStream.class),
//        _7z("7z", SevenZArchiveEntry.class,SevenZArchiveInputStream.class, SevenZArchiveOutputStream.class);

//
        brotli("br", false, null, BrotliCompressorInputStream.class, null),
        bzip2("bzip2", false, null, BZip2CompressorInputStream.class, BZip2CompressorOutputStream.class),
        gzip("gz", false, null, GzipCompressorInputStream.class, GzipCompressorOutputStream.class),
        pack200("pack200", false, null, Pack200CompressorInputStream.class, Pack200CompressorOutputStream.class),
        xz("xz", false, null, XZCompressorInputStream.class, XZCompressorOutputStream.class),
        lzma("lzma", false, null, LZMACompressorInputStream.class, LZMACompressorOutputStream.class),
        snappy_framed("snappy-framed", false, null, FramedSnappyCompressorInputStream.class,
                FramedSnappyCompressorOutputStream.class),
        snappy_raw("snappy-raw", false, null, FramedSnappyCompressorInputStream.class,
                SnappyCompressorOutputStream.class),
        z("z", false, null, ZCompressorInputStream.class, null),
        deflate("deflate", false, null, DeflateCompressorInputStream.class, DeflateCompressorOutputStream.class),
        deflate64("deflate64", false, null, Deflate64CompressorInputStream.class, null),
        lz4_block("lz4-block", false, null, BlockLZ4CompressorInputStream.class, BlockLZ4CompressorOutputStream.class),
        lz4_framed("lz4-framed", false, null, FramedLZ4CompressorInputStream.class,
                FramedLZ4CompressorOutputStream.class),
        zstandard("zstd", false, null, ZstdCompressorInputStream.class, ZstdCompressorOutputStream.class);

        private String type;
        private boolean archive = true;

        private CompressType(String name, boolean isArchive, Class<? extends ArchiveEntry> archiveEntryClass,
                Class<? extends InputStream> archiveInputStreamClass,
                Class<? extends OutputStream> archiveOutputStreamClass) {
            this.type = name;
            this.archive = isArchive;
        }

        public String getType() {
            return type;
        }

    }

    private CompressUtils() {
    }

    // tar
    public static final void tarFile(File file, File tarfile) throws IOException {
        TarArchiveOutputStream taos = new TarArchiveOutputStream(new FileOutputStream(tarfile));
        tarFile(file, taos);
    }

    public static final void tarFile(File file, TarArchiveOutputStream taos) throws IOException {
        TarArchiveEntry tae = new TarArchiveEntry(file);
        tae.setSize(file.length());// 大小
        tae.setName(file.getName());// 不设置会默认全路径
        tae.setMode(0555);
        taos.putArchiveEntry(tae);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int count;
        byte data[] = new byte[1024];
        while ((count = bis.read(data, 0, 1024)) != -1) {
            taos.write(data, 0, count);
        }
        bis.close();
        taos.closeArchiveEntry();
    }

    public static final void untarFile(String destPath, File tarfile) throws IOException {
        TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(tarfile));
        untarFile(destPath, tais);

    }

    public static final void untarFile(String destPath, TarArchiveInputStream tais) throws IOException {
        TarArchiveEntry tae = null;
        while ((tae = tais.getNextTarEntry()) != null) {
            String dir = destPath + File.separator + tae.getName();// tar档中文件
            File dirFile = new File(dir);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
            StreamUtils.streamToOutputStream(tais, bos);
            StreamUtils.closeQuietly(tais, bos);
        }
    }

    // zip
    public static final void zipFile(File file, File tarfile) throws IOException {
        java.util.zip.ZipOutputStream taos = new ZipOutputStream(new FileOutputStream(tarfile));
        ZipEntry tae = new ZipEntry(file.getName());
        tae.setSize(file.length());// 大小
        taos.putNextEntry(tae);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        StreamUtils.streamToOutputStream(bis, taos);
        StreamUtils.closeQuietly(bis, taos);
    }

    public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
        archiveFile(new File("pom.xml"), new File("pom.tar"), CompressType.zip);
        compressFile(new File("pom.xml"), new File("pom.tar"), CompressType.zip);
    }

    private static void compressFile(File sourceFile, File destFile, CompressType compressType)
            throws CompressorException, IOException {
        if (sourceFile.exists()) {
            org.apache.commons.compress.compressors.CompressorOutputStream compressorOutput = CompressorStreamFactory
                    .getSingleton()
                    .createCompressorOutputStream(compressType.getType(), new FileOutputStream(destFile));
            compressFile(sourceFile, compressorOutput, compressType);
            compressorOutput.close();
        }
    }

    public static final void compressFile(File sourceFile,
            org.apache.commons.compress.compressors.CompressorOutputStream archivevStream, CompressType compressType)
            throws IOException {
        if (sourceFile.isDirectory()) {
            if (sourceFile.isDirectory()) {
                for (File f : sourceFile.listFiles()) {
                    compressFile(f, archivevStream, compressType);
                }
            } else {
//                ArchiveEntry tae = archivevStream.createArchiveEntry(sourceFile, sourceFile.getName());
//                if (tae instanceof ZipArchiveEntry) {
//                    ((ZipArchiveEntry) tae).setUnixMode(0755);
//                }
//                archivevStream.putArchiveEntry(tae);

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
                StreamUtils.streamToOutputStream(bis, archivevStream);

                bis.close();
//                archivevStream.closeArchiveEntry();
//                archivevStream.finish();
                archivevStream.flush();
            }
        }
    }

    //
    public static final void archiveFile(File sourceFile, File destFile, CompressType compressType)
            throws IOException, ArchiveException {
        if (sourceFile.exists()) {

            org.apache.commons.compress.archivers.ArchiveOutputStream archivevStream = new ArchiveStreamFactory()
                    .createArchiveOutputStream(compressType.getType(), new FileOutputStream(destFile));
            archiveFile(sourceFile, archivevStream, compressType);
            archivevStream.close();
        }
    }

    public static final void archiveFile(File sourceFile,
            org.apache.commons.compress.archivers.ArchiveOutputStream archivevStream, CompressType compressType)
            throws IOException {
        if (sourceFile.isDirectory()) {
            if (sourceFile.isDirectory()) {
                for (File f : sourceFile.listFiles()) {
                    archiveFile(f, archivevStream, compressType);
                }
            } else {
                ArchiveEntry tae = archivevStream.createArchiveEntry(sourceFile, sourceFile.getName());
                if (tae instanceof ZipArchiveEntry) {
                    ((ZipArchiveEntry) tae).setUnixMode(0755);
                }
                archivevStream.putArchiveEntry(tae);

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
                StreamUtils.streamToOutputStream(bis, archivevStream);

                bis.close();
                archivevStream.closeArchiveEntry();
                archivevStream.finish();
                archivevStream.flush();
            }
        }
    }
}