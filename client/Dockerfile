FROM node:18.14.1

RUN npm install -g @angular/cli

WORKDIR /app
COPY package.json ./

RUN npm install
COPY ./ ./


EXPOSE 4200

CMD ["ng", "serve"]
