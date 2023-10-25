# cipher-master
Creating a compact and reliable solution to handle compress+decompress operations on any cipher of your choice.

## What all are algorithms supported?
- LZMA
- ZSTD
- GZIP
- LIMO_ZSTD
- ZSTD_Proto
- GZIP_Proto

## What are use-cases the solution plays a good role?

Any place where you want to compress data before storing in record-size constrained document DB (OR) decompress data for manipulating existing document.

## How does client interacts with solution?
Service for clients carried over API interaction between parties

## How's this solution stands out?
- Support available for multiple cipher algorithms
- Low latent(~3ms)
- High throughput achieved by introducing parallelism
- And what not to say YES😁 to use it

Feel free take pull from dockerhub: [b0a04gl/cipher-master](https://hub.docker.com/repository/docker/b0a04gl/cipher-master/general)
