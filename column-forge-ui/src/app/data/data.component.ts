import {Component, Input} from '@angular/core';
import {Papa} from 'ngx-papaparse';
import {ClickMode, Engine, HoverMode, MoveDirection, OutMode} from "tsparticles-engine";
import {loadFull} from 'tsparticles';
import {DataService} from '../services/data.service';
import {PromptService} from '../services/prompt.service';

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['data.component.scss']
})
export class DataComponent {
  particlesOptions = {
    background: {
      color: {
        value: "#424242",
      },
    },
    fpsLimit: 30,
    interactivity: {
      events: {
        onClick: {
          enable: true,
          mode: ClickMode.push,
        },
        onHover: {
          enable: true,
          mode: HoverMode.repulse,
        },
        resize: true,
      },
      modes: {
        push: {
          quantity: 4,
        },
        repulse: {
          distance: 200,
          duration: 0.4,
        },
      },
    },
    particles: {
      color: {
        value: "#E92E2E",
      },
      links: {
        color: "#E92E2E",
        distance: 200,
        enable: true,
        opacity: 0.5,
        width: 1,
      },
      collisions: {
        enable: true,
      },
      move: {
        direction: MoveDirection.none,
        enable: true,
        outModes: {
          default: OutMode.bounce,
        },
        random: true,
        speed: 1,
        straight: false,
      },
      number: {
        density: {
          enable: true,
          area: 800,
        },
        value: 50,
      },
      opacity: {
        value: 0.5,
      },
      shape: {
        type: "circle",
      },
      size: {
        value: {min: 1, max: 5},
      },
    },
    detectRetina: true,
  };

  hideParticles = false;

  async particlesInit(engine: Engine): Promise<void> {
    await loadFull(engine);
  }

  constructor(public dataService: DataService, public promptService: PromptService, private papa: Papa) {
  }

  readFile(event: Event) {
    if (event.target) {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files.length === 1) {
        const file = input.files[0];
        this.papa.parse(file, {
          complete: (result) => {
            this.dataService.data = {
              type: 'complex',
              header: result.data[0],
              records: result.data.slice(1)
            }
          }
        });
      }
    }
  }

  addPlaceholder(header: string) {
    if (header !== 'forgedColumn') {
      this.promptService.prompt = this.promptService.prompt + '${' + header + '}'
    }
  }
}
