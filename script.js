const crypto = require('crypto');
const secret = 'Zm9vYmFyMTIzNDU2Nzg5MDEyMzQ1Njc4OTEyMw==';
const header = Buffer.from(JSON.stringify({alg: 'HS256', typ: 'JWT'})).toString('base64url');
const payload = Buffer.from(JSON.stringify({sub: 'admin', iat: Math.floor(Date.now()/1000), exp: Math.floor(Date.now()/1000) + 3600})).toString('base64url');
const signature = crypto.createHmac('sha256', secret).update(header + '.' + payload).digest('base64url');
const token = header + '.' + payload + '.' + signature;
console.log(token);
