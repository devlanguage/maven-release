package org.third.net.ftps;

import java.util.Date;

/**
 * A class that represents a single ftp path element. Used because multiple 
 * protocols create different incompatible representation of the same idea, 
 * but we need a single one to base the FTPModels off of.
 */
public class FTPElement
{
    private String name;
    private String fullPath;
    private boolean directory;
    private long fileSize;
    private Date timestamp;

    /**
     * @param name
     * @param fullPath
     * @param isDir
     */
    public FTPElement(String fullPath, boolean isDir, long fileSize, Date timestamp)
    {
        super();
        String[] pathParts = fullPath.split("/");
        if (pathParts.length > 0)
        {
            name = pathParts[pathParts.length - 1];
        }
        else
        {
            name = "/";
        }
        this.fullPath = fullPath;
        this.fileSize = fileSize;
        this.timestamp = timestamp;
        directory = isDir;
    }

    /**
     * @return the file or directory name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name updates the file or directory name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return - the full path to the file or folder 
     */
    public String getFullPath()
    {
        return fullPath;
    }

    /**
     * @param fullPath - updates the full path to the file or folder
     */
    public void setFullPath(String fullPath)
    {
        this.fullPath = fullPath;
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * @return true if this instance is a folder/directory false if it is a file
     */
    public boolean isDirectory()
    {
        return directory;
    }
    
    public boolean isDir()
    {
        return directory;
    }
    
    public long size()
    {
        return fileSize;
    }
    
    public Date lastModified()
    {
        return timestamp;
    }
}

