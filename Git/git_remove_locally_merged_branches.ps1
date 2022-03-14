git branch --format '%(refname:lstrip=2)' --merged | Where-Object { \$_ -notmatch 'master|HEAD' } | ForEach-Object { git branch \$_ --delete }
