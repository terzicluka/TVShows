var express = require('express');
var app = express();
var jwt = require('express-jwt');
var jwks = require('jwks-rsa');
var cors = require('cors')
var axios = require('axios');

app.use(cors());

var port = 9000;

var jwtCheck = jwt({
    secret: jwks.expressJwtSecret({
        cache: true,
        rateLimit: true,
        jwksRequestsPerMinute: 5,
        jwksUri: 'https://dev-5fsypyq5.us.auth0.com/.well-known/jwks.json'
    }),
    audience: 'http://localhost:4000/',
    issuer: 'https://dev-5fsypyq5.us.auth0.com/',
    algorithms: ['RS256']
});

app.use(jwtCheck);

app.get('/profile', async function (req, res) {
    try {
        const accessToken = req.headers.authorization.split(' ')[1];
        const userInfo = await axios.get('https://dev-5fsypyq5.us.auth0.com/userinfo', {
            headers: {
                authorization: `Bearer ${accessToken}`
            }
        });
        res.send(userInfo.data);
    } catch (err) {
        res.send(err.message);
    }
});

app.listen(port);