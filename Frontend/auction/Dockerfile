FROM node:lts-alpine3.15

RUN npm install -g http-server

WORKDIR /app

COPY package*.json ./

RUN npm install --legacy-peer-deps

COPY . .

RUN npm run build

EXPOSE 8081
CMD [ "http-server", "dist" ]