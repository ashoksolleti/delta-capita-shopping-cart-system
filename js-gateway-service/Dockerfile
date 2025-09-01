FROM node:20-alpine
WORKDIR /app
COPY package.json .
RUN npm install --omit=dev
COPY src ./src
EXPOSE 3000
ENV JAVA_BASE_URL=http://localhost:8080
CMD ["npm","start"]
