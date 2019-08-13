#!/bin/bash

# This file is part of sh-scripts project.
#
# sh-scripts project is free software: you can redistribute it and/or
# modify it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

# =====================================================================
# build-tex (LaTeX Builder)
# =====================================================================
#
# The filename itself explain a lot of this script. This script will
# build .tex file and the bibliography, then cleans the generated
# files.
#
# Usage:
#   - Change the 'FILENAME' variable according to .tex root file,
#     without the trailing .tex extension.
#   - Use 'build-tex [no]' to build tex. [no] option means compile
#     only, without opening the .pdf file or cleaning generated files.
#
# Notes:
#   - To change shell output, change the 'MODE' variable. Currently,
#     the values are [1, 2, 3].
#
# Author   : Edrick
# Version   : v0.1.0
# Changelog :
#   - v0.1.0
#     - Initial version
#
# =====================================================================

MODE=2
FILENAME="skripsi"
PDF_VIEWER="xdg-open"

case $MODE in
    1)
        pdflatex "$FILENAME.tex"
        bibtex "$FILENAME.aux"
        pdflatex "$FILENAME.tex"
        pdflatex "$FILENAME.tex"
        ;;

    2)
        texfot pdflatex "$FILENAME.tex"
        texfot bibtex "$FILENAME.aux"
        texfot pdflatex "$FILENAME.tex"
        texfot pdflatex "$FILENAME.tex"
        ;;

    3)
        pdflatex -shell-escape -interaction=nonstopmode -file-line-error "$FILENAME.tex" | egrep --color -i ".*:[0-9]*:.*"
        bibtex "$FILENAME.aux"
        pdflatex -shell-escape -interaction=nonstopmode -file-line-error "$FILENAME.tex" | egrep --color -i ".*:[0-9]*:.*"
        pdflatex -shell-escape -interaction=nonstopmode -file-line-error "$FILENAME.tex" | egrep --color -i ".*:[0-9]*:.*"
        ;;
esac

if [ "$1" = "no" ]; then
    exit
fi

$PDF_VIEWER $FILENAME.pdf >/dev/null 2>&1 &

for f in `find -regextype posix-extended -regex '.*\.(aux|bbl|blg|log|lof|out|toc|lot)$'`; do
    rm -f $f
done
