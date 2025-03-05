# 🚀 Proyecto de Microservicios en Azure con Docker
Este repositorio contiene un sistema basado en **microservicios**, desplegado con **Docker y Docker Compose** en una **Máquina Virtual de Azure**. La aplicación incluye autenticación, gestión de empleados y registros, y un frontend para la interacción con los servicios.

---

## 📌 **Tecnologías Utilizadas**
- 🐳 **Docker** y **Docker Compose** para la contenerización.
- ☁ **Azure** como plataforma de despliegue.
- 🏗 **Spring Boot** para los microservicios backend.
- 🛠 **MySQL** y **PostgreSQL** como bases de datos.
- ⚡ **React con Vite** como frontend.
- 🔑 **OAuth2 con Spring Security** para autenticación.

---

## 📌 Requisitos Previos
Antes de desplegar, asegúrate de que tienes:
- Una Máquina Virtual en Azure (Linux, con Ubuntu recomendado).
- Docker y Docker Compose instalados en la VM.
- Puertos abiertos en Azure para los servicios (5173, 8080, 9000, 8003, 8002, 3307, 5433).
 
---

## 📂 **Estructura del Proyecto**

```bash
📦 proyecto
┣ 📂 auth-server         # Microservicio de autenticación
┣ 📂 client-app          # API principal
┣ 📂 micro-empleado      # Microservicio de empleados
┣ 📂 micro-registro      # Microservicio de registros
┣ 📂 frontend            # Aplicación frontend en React
┣ 📜 docker-compose.yml  # Archivo para levantar todos los servicios
┗ 📜 README.md           # Este archivo 🚀
```

---

## 🔧 1️⃣ Instalación de Docker y Docker Compose en la VM
Ejecuta estos comandos en la VM para instalar Docker y Docker Compose:

```bash
sudo apt update
sudo apt install docker docker-compose -y
sudo usermod -aG docker $(whoami)
```
📌 Sal y vuelve a entrar a la sesión SSH para aplicar los permisos:

```bash
exit
ssh usuario@MI_IP_AZURE
```
---

## 📂 2️⃣ Configurar la Carpeta del Proyecto en la VM

1️⃣ Crear la carpeta donde se alojará el proyecto:

```bash
mkdir -p ~/proyecto
cd ~/proyecto
```
2️⃣ Crear los archivos .env en la VM:

```bash
cd proyecto
sudo nano docker-compose.yml
cd auth-server
sudo nano .env
cd ..
cd client-app
sudo nano .env
cd ..
cd micro-empleado
sudo nano .env
cd ..
cd micro-registro
sudo nano .env
cd ..
```
## 📦 3️⃣ Desplegar los Contenedores con Docker Compose

1️⃣ Navega a la carpeta del proyecto en la VM:

```bash
cd ~/proyecto
```
2️⃣ Ejecuta Docker Compose para descargar las imágenes y levantar los servicios:

```bash
docker-compose up -d
```
3️⃣ Verifica que los contenedores están corriendo:

```bash
docker ps
```
📌 Todos los servicios deben aparecer con Up y sin errores.
Si un contenedor se apagó, revisa sus logs con:
```bash
docker logs micro-empleado
```
---

## 🔥 4️⃣ Probar el Despliegue

📌 Abre en el navegador:
- 🌍 Frontend: http://MI_IP_AZURE:5173
- 🔑 Auth Server: http://MI_IP_AZURE:9000/oauth2/authorize
- 📂 Micro Empleados: http://MI_IP_AZURE:8003/api/empleados
- 📂 Micro Registros: http://MI_IP_AZURE:8002/api/registros
- 🚀 API Principal: http://MI_IP_AZURE:8080

---

## ♻ 5️⃣ Actualizar Imágenes y Reiniciar Servicios

Si subes una nueva versión de alguna imagen a Docker Hub, actualiza en la VM con:

```bash
docker-compose pull
docker-compose up -d --force-recreate
```
Si solo quieres reiniciar un microservicio:

```bash
docker-compose restart micro-empleado
```

Para apagar todo:

```bash
docker-compose down
```

---

## 🛠 6️⃣ Solución de Problemas

🚨 Ver contenedores apagados:

```bash
sudo docker ps -a
```

🔍 Ver logs de un contenedor:

```bash
docker logs micro-empleado
```

🚀 Ver si un puerto está abierto en la VM:

```bash
sudo netstat -tulnp | grep 8003
```

---

## 📜 Licencia

Este proyecto está bajo la licencia MIT.

---

## ✨ Autores

- 🐢 **Kubernenes**
- 🚀 Colaboradores: Par latas de RedBull®, *shoutout* a AlfaNet® por no caerse nunca

---

## 🎯 Conclusión

- 🚀 Este repositorio permite desplegar y gestionar microservicios de manera eficiente usando Docker en Azure.
- 🔥 Si tienes dudas o sugerencias, abre un Issue o crea un Pull Request.
- 🦾 ¡Dale con todo y disfruta del poder de los microservicios!
 

