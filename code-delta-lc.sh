#!/bin/sh

echo "Delta Line Count"
git log  --pretty=tformat: --numstat | grep src | perl -ane'$i += $F[0]; $d += $F[1]; $c = $i-$d; END{ print "Added lines: $i Removed lines: $d Current lines: $c\n"}'
