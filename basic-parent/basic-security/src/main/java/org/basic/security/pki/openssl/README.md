## openssl
\# keysize:  1~
openssl dsaparam 1024 -out dsaparam
openssl dsaparam 1024 -out dsaparam.key -genkey

openssl gendsa -out dsa dsaparam
openssl gendsa -out dsa.crypt dsaparam -aes128             

\#keysize: 256, 384 or 521
ssh-keygen -f ecdsa -t ecdsa -b 521 -N '' -C 'ecdsa '                    
ssh-keygen -f ecdsa.crypt -t ecdsa -b 521 -N 'test1' -C 'ecdsa '                    
openssl ec -in ecdsa.crypt -text -passin pass:test1
 
\# keysize: 1204,2048
ssh-keygen -f rsa -t rsa -b 2048 -N ''
ssh-keygen -f rsa.crypt -t rsa -b 2048 -N 'test1'
\## generate pkcs1
openssl genrsa -out rsa 1024
openssl genrsa -out rsa.crypt -aes128 -passout pass:test1 1024
openssl rsa -in rsa.crypt -text -passin pass:test1
\## conver to pkcs8
openssl pkcs8 -topk8 -in rsa -out rsa.pk8 -nocrypt
openssl pkcs8 -topk8 -in rsa -out rsa.pk8.crypt -passout pass:test1

\# keysize: 256-16384
ssh-keygen -f ed25519 -t ed25519 -b 521 -N '' -C 'ed25519'   
ssh-keygen -f ed25519.crypt -t ed25519 -b 256 -N 'test1' -C 'ed25519'      