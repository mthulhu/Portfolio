# Rescue Dog Data Web Application

## Description

This project is a web application that connects to a local MongoDB instance to display rescue dog data. It follows the Model-View-Controller (MVC) design pattern and is fully containerized using Docker. The application consists of a database layer, an API layer, and a front-end layer.

## Features

- **Database Layer:** MongoDB backend for storing and querying rescue dog data.
- **API Layer:** Python-based API for interacting with the database.
- **Front-End Layer:** Dash and Jupyter Notebook interface for data visualization and interaction.
- **Containerized:** Easily deployable using Docker and Docker Compose.

## Technology Stack

- **Backend Database:** MongoDB (local instance)
- **API:** Python (Flask, PyMongo)
- **Front End:** Dash, Plotly, Jupyter Notebook, Dash-Leaflet
- **Containerization:** Docker, Docker Compose

## Getting Started

### Prerequisites

- [Docker](https://www.docker.com/get-started) installed on your machine

### Build and Run

1. **Start the application using Docker Compose:**
   ```sh
   docker-compose up --build
   ```

2. **Access the application:**
   - Jupyter Notebook: [http://localhost:8888](http://localhost:8888)
   - Dash App: [http://localhost:8050](http://localhost:8050) 

## Usage

- Use the Jupyter Notebook interface to interact with and visualize rescue dog data.
- The Dash dashboard provides interactive data visualizations.
- The API layer handles communication between the front end and the MongoDB backend.
- A web form allows data entry