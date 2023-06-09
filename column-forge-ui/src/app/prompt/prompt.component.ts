// @ts-nocheck

import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import ace, {Ace} from 'ace-builds';
import {PromptService} from '../services/prompt.service';
import {BehaviorSubject, Subscription} from 'rxjs';

const oop = ace.require('ace/lib/oop');
const TextMode = ace.require('ace/mode/text').Mode;
const TextHighlightRules = ace.require('ace/mode/text_highlight_rules').TextHighlightRules;

const CustomHighlightRules = function () {


  /*
   * NOTICE: The current rule set is a slightly adapted excerpt from https://github.com/sujoyu/plantuml-previewer
   */
  this.$rules = {
    start: [
      {
        token: "support.function.source.wsd",
        regex: /\$\{[\w\s]+\}/
      }
    ]
  };
};

oop.inherits(CustomHighlightRules, TextHighlightRules);

const Mode = function () {
  this.HighlightRules = CustomHighlightRules;
};
oop.inherits(Mode, TextMode);

(function () {
  this.$id = 'ace/mode/column-forge';
}).call(Mode.prototype);

@Component({
  selector: 'app-prompt',
  templateUrl: './prompt.component.html',
  styleUrls: ['prompt.component.scss']
})
export class PromptComponent implements OnInit, AfterViewInit, OnDestroy {

  private editor: Ace.Editor;

  @ViewChild('editor')
  editorRef?: ElementRef<HTMLDivElement>;

  @Input()
  title?: string;

  @Input()
  promptSubject?: BehaviorSubject<string | undefined>;

  @Input()
  placeholders?: string[];

  private promptChangedSubscription?: Subscription;

  constructor(private changeDetectorRef: ChangeDetectorRef) {

  }

  ngOnInit() {
    ace.config.set('basePath', '/assets/ui/');
    ace.config.set('modePath', '');
    ace.config.set('themePath', '');
  }

  ngAfterViewInit(): void {
    if (this.editorRef) {

      this.editor = ace.edit(this.editorRef.nativeElement);
      this.editor.setTheme('ace/theme/clouds_midnight');
      this.editor.setShowPrintMargin(false);
      this.editor.session.setMode(new Mode);
      this.editor.setOptions({
        enableBasicAutocompletion: true
      });

      let lockEditorUpdate = false;
      this.editor.session.on('change', () => {
        if (!lockEditorUpdate) {
          const newValue = this.editor.getValue();
          if (this.promptSubject.value !== newValue) {
            lockEditorUpdate = true;
            this.promptSubject.next(newValue);
            this.changeDetectorRef.detectChanges();
            lockEditorUpdate = false;
          }
        }
      });

      this.promptChangedSubscription = this.promptSubject.subscribe(prompt => {
        if (!lockEditorUpdate) {
          lockEditorUpdate = true;
          this.editor.setValue(prompt, 1);
          lockEditorUpdate = false;
        }
      });

      this.changeDetectorRef.detectChanges();
    }
  }

  ngOnDestroy() {
    this.promptChangedSubscription?.unsubscribe();
  }

  onResized($event: ResizeObserverEntry) {
    if (this.editor) {
      const newContainerHeight = `${$event.contentRect.height}px`;
      const newContainerWidth = `${$event.contentRect.width}px`;

      if (this.editor.container.style.height === newContainerHeight
        && this.editor.container.style.width === newContainerWidth) {
        return;
      }

      this.editor.container.style.width = newContainerWidth;
      this.editor.container.style.height = newContainerHeight;
      this.editor.resize();
    }
  }
}
