OUTPUT_DIR=output
PREVIEW_DIR=previews
RESUME_NAME=alysson-cirilo-resume
YAML_RESUME=data/resume.yml
MAKE_RESUME=make-resume/src/app/cli/build/libs/cli-uber.jar

.PHONY: all
all: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf $(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf $(OUTPUT_DIR)/markdown/$(RESUME_NAME).md

.PHONY: awesome
awesome: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf 

.PHONY: sober
sober: $(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf 

.PHONY: markdown
markdown: $(OUTPUT_DIR)/markdown/$(RESUME_NAME).md

.PHONY: previews
previews: $(PREVIEW_DIR)/sober-resume-preview.png $(PREVIEW_DIR)/awesome-resume-preview.png

$(OUTPUT_DIR)/awesome/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(OUTPUT_DIR)/awesome
	java -jar $(MAKE_RESUME) -f awesome -i $(YAML_RESUME) > $@

$(OUTPUT_DIR)/sober/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(OUTPUT_DIR)/sober
	java -jar $(MAKE_RESUME) -f sober -i $(YAML_RESUME) > $@

$(OUTPUT_DIR)/markdown/$(RESUME_NAME).md: $(MAKE_RESUME) | $(OUTPUT_DIR)/markdown
	java -jar $(MAKE_RESUME) -f markdown -i $(YAML_RESUME) > $@

$(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).tex
	cp -r dependencies/awesome/Awesome-CV $(OUTPUT_DIR)/awesome
	cd output/awesome && xelatex $(RESUME_NAME).tex

$(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf: $(OUTPUT_DIR)/sober/$(RESUME_NAME).tex
	cp dependencies/sober/* $(OUTPUT_DIR)/sober
	cd output/sober && xelatex $(RESUME_NAME).tex

$(PREVIEW_DIR)/sober-resume-preview.png: $(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

$(PREVIEW_DIR)/awesome-resume-preview.png: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

.PHONY: $(MAKE_RESUME)
$(MAKE_RESUME):
	cd make-resume && ./gradlew uberJar

$(OUTPUT_DIR)/awesome:
	mkdir -p $@
$(OUTPUT_DIR)/sober:
	mkdir -p $@
$(OUTPUT_DIR)/markdown:
	mkdir -p $@

$(PREVIEW_DIR)/:
	mkdir -p $@

.PHONY: clean
clean:
	rm -rf $(OUTPUT_DIR)/
