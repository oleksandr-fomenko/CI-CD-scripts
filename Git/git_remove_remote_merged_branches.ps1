git branch --format '%(refname:lstrip=-1)' --remote --merged | Where-Object { \$_ -notmatch 'master|HEAD' } | ForEach-Object { git push origin --delete \$_; }
