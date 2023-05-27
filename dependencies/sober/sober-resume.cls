\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{sober-resume}
\LoadClass[a4paper,11pt]{article}

\RequirePackage{fontspec}
\setmainfont{EB Garamond}
\RequirePackage{fourier-orns}
\RequirePackage{titlesec}
\RequirePackage{enumitem}
\RequirePackage[xetex,hidelinks]{hyperref}
\RequirePackage{fancyhdr}
\RequirePackage[
    a4paper,
    top=1.6cm,
    bottom=1.2cm,
    left=1.2cm,
    right=1.2cm,
    includefoot,
    footskip = 10.73pt,
%showframe,
]{geometry}
\RequirePackage{fontawesome}

% page formatting
\pagestyle{fancy}
\fancyhf{} % clear all header and footer fields
\fancyfoot{}
\renewcommand{\headrulewidth}{0pt}
\renewcommand{\footrulewidth}{0pt}

\raggedbottom
\raggedright
\setlength{\tabcolsep}{0in}

% section formatting
\titleformat{\section}
{\scshape\raggedright\large}
{}
{0em}
{}[\titlerule]

% list formatting
\setlist[1]{leftmargin=*, itemsep=-3pt}
\setlist[2]{topsep=-3pt, itemsep=-2.5pt, before*=\small}
\renewcommand{\labelitemii}{\footnotesize$\circ$}

% custom commands

\newcommand{\iconhref}[2]{\href{#1}{#2~\textsuperscript{\tiny{\faExternalLink}}}}

% companyUrl, companyName, location, positionName, positionDate
\newcommand{\employment}[5]{
    \company{#1}{#2}{#3}
    \position{#4}{#5}
}

% companyUrl, companyName, location
\newcommand{\company}[3]{
    \begin{tabular*}{0.97\textwidth}{l@{\extracolsep{\fill}}r}
        \iconhref{#1}{\textbf{#2}} & {\small#3}\\
    \end{tabular*}
}

% positionName, positionDate
\newcommand{\position}[2]{
    \begin{tabular*}{0.97\textwidth}{l@{\extracolsep{\fill}}r}
        \textit{\small#1} & \textit{\small#2} \\
    \end{tabular*}
}

% projectUrl, projectName, projectDescription
\newcommand{\project}[3]{
    \small{\textbf{\iconhref{#1}{#2}}{:~#3}}
}

\newcommand*{\name}[1]{
    \newcommand{\@name}{#1}
}
\newcommand{\@mytitle}{
    {\huge\textbf{\@name}}
}
\newcommand*{\headline}[1]{
    \newcommand{\@headline}{#1}
}
\newcommand*{\email}[2]{
    \newcommand{\@email}{\iconhref{#1}{{\scriptsize\faEnvelope{}} #2}}
}
\newcommand*{\linkedin}[2]{
    \newcommand{\@linkedin}{\iconhref{#1}{\faLinkedin{} #2}}
}
\newcommand*{\github}[2]{
    \newcommand{\@github}{\iconhref{#1}{\faGithub{} #2}}
}
\newcommand*{\address}[2]{
    \newcommand{\@address}{\hspace{1pt}\iconhref{#1}{\faMapMarker{}\hspace{1pt} #2}}
}
    
\newcommand{\makeheader}{
    \begin{minipage}[t]{0.70\linewidth}%743
        \@mytitle\\
        \@headline
    \end{minipage}
    \hspace{8pt}
    \begin{minipage}[t]{0.277\linewidth}
        {\flushleft\small
            \@email\\
            \@linkedin\\
            \@github\\
            \@address
        }
    \end{minipage}
}

