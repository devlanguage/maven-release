## sshkey-gen
\# keysize: 1024 
ssh-keygen -f dsa -t dsa -b 1024 -N ''
ssh-keygen -f dsa.crypt -t dsa -b 1024 -N 'test1'    
openssl dsa -in dsa.crypt -text -passin pass:test1    

\#keysize: 256, 384 or 521
ssh-keygen -f ecdsa -t ecdsa -b 521 -N '' -C 'ecdsa '                    
ssh-keygen -f ecdsa.crypt -t ecdsa -b 521 -N 'test1' -C 'ecdsa '                    
openssl ec -in ecdsa.crypt -text -passin pass:test1
 
\# keysize: 1204,2048
ssh-keygen -f rsa -t rsa -b 2048 -N ''
ssh-keygen -f rsa.crypt -t rsa -b 2048 -N 'test1'
openssl rsa -in rsa.crypt -text -passin pass:test1

\# keysize: 256-16384
ssh-keygen -f ed25519 -t ed25519 -b 521 -N '' -C 'ed25519'   
ssh-keygen -f ed25519.crypt -t ed25519 -b 256 -N 'test1' -C 'ed25519'      