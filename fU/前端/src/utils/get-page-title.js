import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Synapsis-C'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
