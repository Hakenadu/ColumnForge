FROM node:14-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- -c production

FROM nginx:alpine
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/column-forge-ui /usr/share/nginx/html
COPY nginx/environment.json /usr/share/nginx/html/
EXPOSE 80
CMD sed -i "s|API_URL_PLACEHOLDER|${API_URL}|g" /usr/share/nginx/html/environment.json && nginx -g 'daemon off;'
