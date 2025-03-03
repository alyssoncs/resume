# Alysson Cirilo's Resume

## I'm here for the resume

Go to the [releses page](https://github.com/alyssoncs/resume/releases) and download the most recent version.

There are a couple of resume flavors you can choose from, grab the one that fits more with your company.

Currently, there is a *fancy* resume, this has a modern feel, with colors and sans serif font, a *sober* resume with a more classic look, black and white and serif font, and there is also a *markdown* version in case you ever need this.

Here is a preview on how they look like:

| Fancy                                                                                                                     | Sober                                                                                                                   | Markdown                                                           |
|---------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| [![fancy resume flavor preview](../resumes/previews/fancy-resume-preview.png)](../../tree/resumes/alysson-cirilo-fancy-resume.pdf) | [![sober resume flavor preview](../../tree/resumes/previews/sober-resume-preview.png)](../../tree/resumes/alysson-cirilo-sober-resume.pdf) | [check here](../../tree/resumes/alysson-cirilo-markdown-resume.md) |

## About this project

### What it does?

This is a program that generates my resume in different formats based on the data in a [YAML file](data/resume.yml). So I can write my resume data in a single place (the YAML file) and the program will convert it to a given format.

### I'm still not bored, how does it work?

In the `make-resume` directory you'll find a Kotlin cli program that can consume a YAML file and convert it to the other formats that can be represented as a String that is outputted to the stdout. It can produce markdown, fancy and sober resumes. Fancy and sober are just [LaTeX](https://www.latex-project.org) documents.

That is a Makefile that will take the YAML file in the `data` directory, input it in the `make-resume` program, save them as files and convert the LaTeX ones into pdf.

## Acknowledgements

This project was made possible by using other people ideas and tools. Here is a non-comprehensive list of important projects worth mentioning.

* [Simon Zeng's Resume](https://github.com/s-zeng/resume): This is where I've taken the idea of having the resume written in a single place and converting it to another formats. This project is basically a copy written in another language.
* [Awesome CV](https://github.com/posquit0/Awesome-CV): This is the LaTeX template used to create the fancy flavor of my resume.
* [Ladislav Vrbsky's Resume](https://github.com/vrbsky/resume-en): The sober resume flavor is a heavily modified version of this resume.

