# 🚀 Proyecto de Microservicios en Azure con Docker
Este proyecto despliega un sistema basado en microservicios utilizando Docker y Docker Compose en una Máquina Virtual de Azure.

---

## 📌 Requisitos Previos
Antes de desplegar, asegúrate de que tienes:
	•	Una Máquina Virtual en Azure (Linux, con Ubuntu recomendado).
	•	Docker y Docker Compose instalados en la VM.
	•	Puertos abiertos en Azure para los servicios (5173, 8080, 9000, 8003, 8002, 3307, 5433).
 
---

## 🔧 1️⃣ Instalación de Docker y Docker Compose en la VM
Ejecuta estos comandos en la VM para instalar Docker y Docker Compose:

```bash
sudo apt update
sudo apt install docker docker-compose -y
sudo usermod -aG docker $(whoami)
```
📌 Sal y vuelve a entrar a la sesión SSH para aplicar los permisos:
