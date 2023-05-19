# Column Forge
Column Forge is an open-source initiative born out of a necessity for improved, context-aware translations. While standard public translation tools offered grammatically correct output, they failed to provide translations that accurately represented the intended context. This was a challenge we encountered while working on internationalizing a website.

To tackle this problem, we utilized the powerful capabilities of the OpenAI API models. By personalizing requests to these models to align with our unique needs, we experienced a notable enhancement in the quality of translations, outperforming conventional public translators.

Column Forge now more generally serves as an innovative application for handling datasets in CSV format. It provides an interactive interface that enables users to specify inputs for a Large Language Model from the OpenAI API, row by row. These inputs are designed around placeholders that map to the column names within the dataset.

With every row input, Column Forge sends a request and then processes the received response, augmenting and recording it in an additional column.

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