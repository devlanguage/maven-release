package org.basic.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class CompressUtils {

	enum CompressType {
		ar("ar", ArArchiveEntry.class, ArArchiveInputStream.class, ArArchiveOutputStream.class),
		arj("arj", ArjArchiveEntry.class, ArjArchiveInputStream.class, null),
		cpio("cpio", CpioArchiveEntry.class, CpioArchiveInputStream.class, CpioArchiveOutputStream.class),
		dump("dump", DumpArchiveEntry.class, DumpArchiveInputStream.class, null),
		jar("jar", JarArchiveEntry.class, JarArchiveInputStream.class, JarArchiveOutputStream.class),
		tar("tar", TarArchiveEntry.class, TarArchiveInputStream.class, TarArchiveOutputStream.class),
		zip("zip", ZipArchiveEntry.class, ZipArchiveInputStream.class, ZipArchiveOutputStream.class);
		// ArchiveStreamFactory.ZIP
//		_7z("7z", SevenZArchiveEntry.class,SevenZArchiveInputStream.class, SevenZArchiveOutputStream.class);
		private String type;
		private Class<? extends ArchiveEntry> archiveEntryClass;
		private Class<? extends ArchiveInputStream> archiveInputStreamClass;
		private Class<? extends ArchiveOutputStream> archiveOutputStreamClass;

		private CompressType(String name, Class<? extends ArchiveEntry> archiveEntryClass,
				Class<? extends ArchiveInputStream> archiveInputStreamClass,
				Class<? extends ArchiveOutputStream> archiveOutputStreamClass) {
			this.type = name;
			this.archiveEntryClass = archiveEntryClass;
			this.archiveInputStreamClass = archiveInputStreamClass;
			this.archiveOutputStreamClass = archiveOutputStreamClass;
		}

		public String getType() {
			return type;
		}

		public Class<? extends ArchiveEntry> getArchiveEntryClass() {
			return archiveEntryClass;
		}

		public Class<? extends ArchiveInputStream> getArchiveInputStreamClass() {
			return archiveInputStreamClass;
		}

		public Class<? extends ArchiveOutputStream> getArchiveOutputStreamClass() {
			return archiveOutputStreamClass;
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
		ZipOutputStream taos = new ZipOutputStream(new FileOutputStream(tarfile));
		zipFile(file, taos);
	}

	public static final void zipFile(File file, ZipOutputStream taos) throws IOException {
		ZipEntry tae = new ZipEntry(file.getName());
		tae.setSize(file.length());// 大小
		taos.putNextEntry(tae);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		StreamUtils.streamToOutputStream(bis, taos);
		StreamUtils.closeQuietly(bis, taos);
	}

	public static void main(String[] args) throws IOException, ArchiveException {
		compressFile(new File("pom.xml"), new File("pom.tar"), CompressType.zip);
	}

	//
	public static final void compressFile(File file, File tarfile, CompressType compressType)
			throws IOException, ArchiveException {
		ArchiveOutputStream output = new ArchiveStreamFactory().createArchiveOutputStream(compressType.getType(),
				new FileOutputStream(tarfile));
		compressFile(file, output, compressType);
	}

	public static final void compressFile(File file, ArchiveOutputStream archivevStream, CompressType compressType)
			throws IOException {
		ArchiveEntry tae = archivevStream.createArchiveEntry(file, file.getName());
		if (tae instanceof ZipArchiveEntry) {
			((ZipArchiveEntry) tae).setUnixMode(0755);
		}
		archivevStream.putArchiveEntry(tae);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		StreamUtils.streamToOutputStream(bis, archivevStream);

		bis.close();
		archivevStream.closeArchiveEntry();
		archivevStream.finish();
		archivevStream.flush();
		archivevStream.close();
	}
}