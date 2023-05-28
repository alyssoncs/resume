OUTPUT_DIR=output
RESUME_NAME=alysson-cirilo-resume
FLAVORS=awesome sober markdown
JSON_RESUME=data/resume.json
MAKE_RESUME=make-resume/app/cli/build/libs/cli-uber.jar
DEPENDS=$(JSON_RESUME) | $(OUTPUT_DIR)/ $(OUTPUT_DIR)/awesome $(OUTPUT_DIR)/sober $(OUTPUT_DIR)/markdown

.PHONY: all
all: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf $(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf $(OUTPUT_DIR)/markdown/$(RESUME_NAME).md

.PHONY: awesome
awesome: $(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf 

.PHONY: sober
sober: $(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf 

.PHONY: markdown
markdown: $(OUTPUT_DIR)/markdown/$(RESUME_NAME).md

$(OUTPUT_DIR)/awesome/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(OUTPUT_DIR)/awesome
	java -jar $(MAKE_RESUME) -f awesome -i $(JSON_RESUME) > $@

$(OUTPUT_DIR)/sober/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(OUTPUT_DIR)/sober
	java -jar $(MAKE_RESUME) -f sober -i $(JSON_RESUME) > $@

$(OUTPUT_DIR)/markdown/$(RESUME_NAME).md: $(MAKE_RESUME) | $(OUTPUT_DIR)/markdown
	java -jar $(MAKE_RESUME) -f markdown -i $(JSON_RESUME) > $@

$(OUTPUT_DIR)/awesome/$(RESUME_NAME).pdf: | $(OUTPUT_DIR)/awesome/$(RESUME_NAME).tex
	cp -r dependencies/awesome/Awesome-CV $(OUTPUT_DIR)/awesome
	cd output/awesome && xelatex $(RESUME_NAME).tex

$(OUTPUT_DIR)/sober/$(RESUME_NAME).pdf: | $(OUTPUT_DIR)/sober/$(RESUME_NAME).tex
	cp dependencies/sober/sober-resume.cls $(OUTPUT_DIR)/sober
	cd output/sober && xelatex $(RESUME_NAME).tex

$(MAKE_RESUME):
	cd make-resume && ./gradlew uberJar

$(OUTPUT_DIR)/awesome:
	mkdir -p $@
$(OUTPUT_DIR)/sober:
	mkdir -p $@
$(OUTPUT_DIR)/markdown:
	mkdir -p $@

.PHONY: clean
clean:
	rm -rf $(OUTPUT_DIR)/
