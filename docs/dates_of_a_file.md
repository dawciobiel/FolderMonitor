# Dates of a file

Unix doesn't keep track of a creation date. The only information that's available is typically the last times the files was:

    Accessed
    Modified
    Changed

        Access - the last time the file was read
        Modify - the last time the file was modified (content has been modified)
        Change - the last time meta data of the file was changed (e.g. permissions)


You can get dates related to a particular file using the stat command.

       $ stat test-file-parity-date.jar
       
       File: test-file-parity.jar
       Size: 2         	Blocks: 8          IO Block: 4096   regular file
       Device: 27h/39d	Inode: 3568237     Links: 1
       Access: (0644/-rw-r--r--)  Uid: ( 1000/dawciobiel)   Gid: ( 1000/dawciobiel)
       Access: 2022-06-12 20:52:13.338449512 +0200
       Modify: 2022-06-12 20:52:13.338449512 +0200
       Change: 2022-06-12 20:52:13.338449512 +0200
       Birth: 2022-06-10 15:15:34.843234019 +0200
