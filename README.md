# Column Forge
We embarked on an initiative to internationalize a website, a journey that led to an unexpected but welcome outcome: the creation of this open-source project, named Column Forge. While public translators offered grammatically correct translations, they failed to provide contextually appropriate interpretations, an issue we experienced firsthand.

Our solution was to leverage the power of OpenAI API models, which proved to be more effective when personalized to suit our unique use case. The exceptional translation performance we experienced with these personalized models was a substantial improvement over the standard public translators we had initially utilized.

When we encapsulated this process into a website, we recognized an opportunity to evolve our work into an open-source project that provided broader functionality.

Column Forge is an innovative application that enables the opening of datasets in CSV format. It provides an interactive interface where users can define inputs row by row for a Large Language Model from the OpenAI API. These inputs are based on placeholders that correspond to the column names of the dataset.

With Column Forge, each row sends a request, and the response is then enhanced and documented in an additional column. This meticulous process results in an improved, contextually appropriate translation.

![](misc/demo.gif)

## Demo & Disclaimer
Although we provide a demo at [column-forge.mseiche.de](https://column-forge.mseiche.de), it is not fully functional due to privacy and warranty considerations. This limitation is because we have not stored an API key on the server. Furthermore, it is not possible to configure your own API key on the frontend, as we've server-side restricted this to a fixed set of API keys belonging to specific individuals.

However, we've made it extremely easy to run the project on your own machine using docker-compose, incorporating your own API key. You can thus launch and use Column Forge locally, enabling you to explore its capabilities in a controlled environment.

For detailed instructions on how to get Column Forge up and running using docker-compose, please refer to the following setup guide.

## Run Column Forge locally
Use the following docker-compose.yml (you can also easily modify [the one in this project](docker-compose.yml)):

```yml
version: '3'
services:
  column-forge-api:
    image: hakenadu/column-forge-api
    container_name: column-forge-api
    environment:
      OPENAI_API_KEY: sk-... # insert your api key here
    ports:
    - 8080:8080
  column-forge-ui:
    image: hakenadu/column-forge-ui
    container_name: column-forge-ui
    environment:
      API_URL: http://localhost:8080
    ports:
    - 80:80
```

Start it using the following command

```bash
docker-compose up -d
```

You can now access the UI at [http://localhost](http://localhost).